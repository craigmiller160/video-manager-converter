#!/bin/sh

function import {
  terraform \
    import \
    -var="onepassword_token=$ONEPASSWORD_TOKEN"\
    "$1" "$2"
}

function plan {
  terraform plan \
    -var "onepassword_token=$ONEPASSWORD_TOKEN"
}

import "keycloak_openid_client.video_manager_converter_dev" "apps-dev/ce88d0b8-07bc-4eed-9066-153247200fa9"
import "keycloak_openid_client.video_manager_converter_prod" "apps-prod/ce38689c-8034-48c5-bb89-8927b7a33aa0"

import "keycloak_role.video_manager_converter_access_role_dev" "apps-dev/2580f310-2993-46ce-a713-dc2fab7cd174"
import "keycloak_role.video_manager_converter_access_role_prod" "apps-prod/d4abb156-7d66-45f5-8dca-0556adb5e7b7"