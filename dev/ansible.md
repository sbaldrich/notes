# Notes on Ansible

Ansible is a configuration management tool that allows to automate IT tasks. It is *agent-less* and works over OpenSSH or *paramiko* (when necessary).

###### Pro tips:

* Ansible configuration is stored in `/etc/ansible/ansible.cfg` but can be overriden with and `ansible.cfg` file on the current directory.

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
