## Notes on Vagrant

 1. [Vagrant](#intro)
 1. [Complex environments setup](#multimachine)

<a name="intro"></a>
### Vagrant
Vagrant works as a wrapper around virtualization software, allowing to speed up tasks associated with setting up, tearing down and sharing virtual machines. It can be used to create disposable development environments to test new things or that resemble production environments.

run `vagrant` with no arguments to see the possible commands.

###### Pro tips:

* Vagrant *boxes* (virtual machines) usually include several required packages installed and a `vagrant` user inside the image.

* The directory of the host machine where the `Vagrantfile` is contained will be mounted inside the boxes on the `/vagrant/` route.

* If you want your machines to be able to see each other, you can consider modifying the hosts file during provisioning. For example in the `bootstrap-mgmt.sh` file used for provisioning the *mgmt* machine on the example [below](#multimachine).


<a name="multimachine"/>
### Setting up a complex environment with Vagrant

It is possible to easily define complex configurations of machines using a single configuration file. The following `Vagrantfile` taken from [here](https://raw.githubusercontent.com/jweissig/episode-45/master/Vagrantfile) displays a configuration consisting of 4 *ubuntu/trusty64* machines distributed as follows:

* A management (*mgmt*) node.
* A load balancer (*lb*) node.
* 2 web (*web#i*) nodes.

All machines can see each other via a private network with ip addresses defined by us. In this case the `bootstrap-mgmt` script that is used to provision the *mgmt* box will install Ansible, copy files from

```ruby
# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|

  # Define the management box
  config.vm.define :mgmt do |mgmt_config|
      # Use the trusty64 as the base image
      mgmt_config.vm.box = "ubuntu/trusty64"
      # Define the host name for this box
      mgmt_config.vm.hostname = "mgmt"
      # Configure the network to be private and set the ip address
      mgmt_config.vm.network :private_network, ip: "10.0.15.10"
      # Set virtualbox as provider for the machine, others are allowed (but may not be free).
      mgmt_config.vm.provider "virtualbox" do |vb|
        #Set provider-specific options
        vb.memory = "256"
      end
      # Set the provision script for the box, in this case a shell script called bootstrap-mgmt will be executed
      mgmt_config.vm.provision :shell, path: "bootstrap-mgmt.sh"
  end

  config.vm.define :lb do |lb_config|
      lb_config.vm.box = "ubuntu/trusty64"
      lb_config.vm.hostname = "lb"
      lb_config.vm.network :private_network, ip: "10.0.15.11"
      # Map the port 8080 in the host machine to the port 80 on the box
      lb_config.vm.network "forwarded_port", guest: 80, host: 8080
      lb_config.vm.provider "virtualbox" do |vb|
        vb.memory = "256"
      end
  end


  # https://docs.vagrantup.com/v2/vagrantfile/tips.html
  (1..2).each do |i|
    config.vm.define "web#{i}" do |node|
        node.vm.box = "ubuntu/trusty64"
        node.vm.hostname = "web#{i}"
        node.vm.network :private_network, ip: "10.0.15.2#{i}"
        node.vm.network "forwarded_port", guest: 80, host: "808#{i}"
        node.vm.provider "virtualbox" do |vb|
          vb.memory = "256"
        end
    end
  end

end
```

### References

* [Sysadmincasts - Learning Ansible with Vagrant](https://sysadmincasts.com/episodes/45-learning-ansible-with-vagrant-part-2-4)
