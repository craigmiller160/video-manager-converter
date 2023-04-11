locals {
  access_role_common = {
    name = "access"
  }
}

resource "keycloak_role" "video_manager_converter_access_role_dev" {
  realm_id = data.keycloak_realm.apps_dev.id
  client_id = keycloak_openid_client.video_manager_converter_dev.id
  name = local.access_role_common.name
}

resource "keycloak_role" "video_manager_converter_access_role_prod" {
  realm_id = data.keycloak_realm.apps_prod.id
  client_id = keycloak_openid_client.video_manager_converter_prod.id
  name = local.access_role_common.name
}