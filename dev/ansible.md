# Notes on Ansible

1. [pro-tips](#protips)
1. [Inventory](#inventory)
1. [Patterns](#patterns)
1. [*ad-hoc* commands](#adhoc)
1. [Playbooks](#playbooks)
1. [Roles](#roles)


Ansible is a configuration management tool that allows to automate IT tasks. It is *agent-less* and works over OpenSSH or *paramiko* (when necessary).

<a name="protips"></a>
###### Pro tips:

* Ansible configuration is stored in `/etc/ansible/ansible.cfg` but can be overriden with and `ansible.cfg` file on the current directory.

* Use the `all` or `*` pattern to target all hosts in the inventory. Negations, conjunctions and disjunctions are also possible, *e.g.,* `ansible web:db:&staging:!phoenix`

* You can set the number of forks of an ad-hoc command using the `-f` flag, *e.g.,* `ansible north-zone -a "/sbin/reboot" -f 10` will reboot all the hosts in the north zone, ten hosts at a time.
* If you're not using roles, you should be.

<a name="inventory"></a>
## Inventory

The inventory file lists all the systems belonging to the infrastructure that Ansible will be able to work with. It is saved in `/etc/ansible/hosts` by default.

```
mail.example.com:5403

[web]
www[01:50].example.com

[db]
baz.example.com
bazz.example.com
```

Some notes on the above inventory file:

* Groups can be defined using brackets and they can be used on **playbooks** in order to execute commands only on a subset of hosts.
* If there are hosts that are not listening on standard ssh hosts, their ports can be specified with a colon. *See mail.example.com above*
* Numeric or alphabetic patterns can be used to define multiple hosts. **Ranges are inclusive.**
* See the hosts that would be affected by the execution of a playbook using the `--list-hosts` of the `ansible-playbook` command.


<a name="patterns"></a>
## Patterns

Use patterns to refer to the host or hosts that should be the target of the *ad hoc* commands. They can include conjunctions, disjunctions, subscripting or regular expressions.

<a name="adhoc"></a>
## *Ad-hoc* Commands

They are commands that are executed outside a playbook. They are useful for quick tasks that must be done once in a while and that span over multiple hosts (although it can be one too) like restarting services, shutting down machines, etc.

The ad-hoc command `ansible web -m ping -u sbaldrich --ask-pass` will use the module ping on the web machines as the user sbaldrich. *The default module is `command`*.

### Useful modules

* `copy`: scp files from the ansible machine to a group of hosts. Optionally set permissions and ownership.
* `ping`: verify that the hosts are accessible and operable by ansible (not quite the same as the ICMP ping)
* `file`: change ownership and permission of files, create or remove files and directories.
* `yum` and `apt`: package managers.
* `command`: execute shell commands.
* `user`: creation and manipulation of user accounts and groups.
* `git`: this one is self-explaining
* `service`: control services
* `authorized_key`: add or remove SSH authorized key

<a name="playbooks"></a>
## Playbooks

Playbooks are the core of ansible, they allow for complex configurations and orchestration. They are expressed using [YAML](http://docs.ansible.com/ansible/YAMLSyntax.html) files. **Playbooks** group **plays**, and plays map a set of hosts to **tasks**.

```yaml
---
- hosts: webservers
  remote_user: root

  tasks:
  - name: ensure apache is at the latest version
    yum: name=httpd state=latest
  - name: write the apache config file
    template: src=/srv/httpd.j2 dest=/etc/httpd.conf

- hosts: databases
  remote_user: root

  tasks:
  - name: ensure postgresql is at the latest version
    yum: name=postgresql state=latest
  - name: ensure that postgresql is started
    service: name=postgresql state=running
```

* The `hosts` line defines a list of one or more groups or host patterns separated by colons to apply the play to.
* `remote_user` specifies the user that will run the task on the remote machine. It can also be specified in a per-task manner. Use `become` and `become_user` for privilege escalation. Remember to use `--ask-become-pass` if you need to provide a password to sudo.
* Each `task` should have a name to describe the task to aid maintainability. The goal of each task is to execute a module with a set of arguments that can optionally be replaced with variables.

### Handlers

Handlers allow to execute tasks in response of some event. The following example task notifies the *restart memcached* and *restart apache* handlers whenever the contents of *foo.conf* changes.

```yaml
- name: template configuration file
  template: src=template.j2 dest=/etc/foo.conf
  notify:
     - restart memcached
     - restart apache
```

Handlers are specified in the same way as tasks:

```yaml
handlers:
    - name: restart memcached
      service: name=memcached state=restarted
    - name: restart apache
      service: name=apache state=restarted
```
<a name="roles"></a>
## Roles

Roles are a way of organizing playbooks that improves organization and reuse. Basically, you set up a directory structure as:

```yaml
site.yml          # Say, a playbook for deploying
webservers.yml    # The main webservers playbook, it invokes roles.
databases.yml
cluster.yml
roles/
  common/     # Files that describe the "common" role
    files/      # Any 'copy' task in "common" can refer to files in here without the path
    templates/
    tasks/      # Anything inside main.yml will be added to the play. This applies to handlers and vars too.
    handlers/
    vars/
    defaults/
    meta/
  webservers/     # Files that describe the "webservers" role
    files/
    templates/
    ...
```

And the `webservers` playbook could look as follows:

```yaml
---
- hosts: webservers
  roles:
    - common
    - webservers
```

Now, this structure will define a behavior where all the **main.yml** files inside the *tasks*, *handlers* and *vars* folders will be added to the play. Additionally, all `copy` and `template` tasks can reference files inside the *files* and *templates* directories without specifying the full path and any `include` task can reference files in *tasks* without having to describe their path.

Roles can also be parameterized:

```yaml
---
- hosts: webservers
  roles:
    - common
    - {role: foo_role, dir: '/opt/dir', port: 5555}
``` 

In conclusion, **use roles**. ಠ_ಠ