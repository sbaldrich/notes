Vagrant.configure(2) do |config|
  config.vm.box = "data-science-toolbox/dst"
  config.vm.network "forwarded_port", guest: 8888, host: 8888
  # config.vm.provision "shell", inline: <<-SHELL
  #    sudo apt-get update
  #    sudo apt-get install -y apache2
  # SHELL
end
