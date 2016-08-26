Exec { path => [ "/bin/", "/sbin/" , "/usr/bin/", "/usr/sbin/" ] }

exec { "update":
  command => "apt-get update",
  path    => "/usr/bin",
}
#Instalacao das dependencias
package { [
  'openjdk-7-jdk',
  'tomcat7',
  'mysql-server',
  'apache2',
  'phpmyadmin',
]:
  ensure => present,
  require => Exec["update"],
}

exec{ 'echo "Include /etc/phpmyadmin/apache.conf" >> /etc/apache2/apache2.conf': 
	command => 'echo "Include /etc/phpmyadmin/apache.conf" >> /etc/apache2/apache2.conf',
	require => Package['apache2'],
}


exec{ 'echo \'SET PASSWORD = PASSWORD("123");\' > script.sql':
	command => 'echo \'SET PASSWORD = PASSWORD("123");\' > script.sql;',
	require => Package['mysql-server'],
}

exec{ 'mysql -u root < script.sql':
	command => 'mysql -u root < script.sql',
	require => Package['mysql-server'],
}

exec{ 'rm -rf script.sql':
	command => 'rm -rf script.sql',
	require => Package['mysql-server'],
}
