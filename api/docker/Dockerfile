FROM @docker.dockerfile.from@
ADD @project.build.finalName@.tar.gz @project.deploy.appPath@/
 CMD ["@project.deploy.appPath@/-","once", "@project.build.finalName@.jar.sh"]
#CMD ["@project.build.finalName@.@project.packaging@.sh","once"]