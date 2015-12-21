# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|
  # create notebook node
  config.vm.define :notebook do |notebook_config|
      notebook_config.vm.box = "ubuntu/trusty64"
      notebook_config.vm.hostname = "notebook"
      notebook_config.vm.network :private_network, ip: "10.0.15.10"
      notebook_config.vm.provider "virtualbox" do |vb|
        vb.memory = "256"
      end
      notebook_config.vm.network "forwarded_port", guest: 8888, host:9999
      notebook_config.vm.provision :shell, path: "bootstrap-notebook.sh"
  end
end
