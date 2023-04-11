# video-manager-converter

A micro-service to help convert files to mp4 for the Video Manager application.

## A Note on Terraform

In addition to the `$ONEPASSWORD_TOKEN` needing to be available as an OS environment variable, this application expects that the `video-manager-server` application is already configured in Keycloak via terraform. This allows for the composite role to be put together.