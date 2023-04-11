locals {
  access_role_common = {
    name = "access"
  }
}

data "keycloak_openid_client" "market_tracker_api_dev" {
  realm_id  = data.keycloak_realm.apps_dev.id
  client_id = "video-manager-server"
}

data "keycloak_openid_client" "market_tracker_api_prod" {
  realm_id  = data.keycloak_realm.apps_prod.id
  client_id = "video-manager-server"
}

data "keycloak_role" "market_tracker_api_access_role_dev" {
  realm_id  = data.keycloak_realm.apps_dev.id
  client_id = data.keycloak_openid_client.market_tracker_api_dev.id
  name = local.access_role_common.name
}

data "keycloak_role" "market_tracker_api_access_role_prod" {
  realm_id  = data.keycloak_realm.apps_prod.id
  client_id = data.keycloak_openid_client.market_tracker_api_prod.id
  name = local.access_role_common.name
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


resource "keycloak_role" "video_manager_all_access_dev" {
  realm_id = data.keycloak_realm.apps_dev.id
  name = "video_manager_all_access"
  composite_roles = [
    data.keycloak_role.market_tracker_api_access_role_dev.id,
    keycloak_role.video_manager_converter_access_role_dev.id
  ]
}

resource "keycloak_role" "video_manager_all_access_prod" {
  realm_id = data.keycloak_realm.apps_prod.id
  name = "video_manager_all_access"
  composite_roles = [
    data.keycloak_role.market_tracker_api_access_role_prod.id,
    keycloak_role.video_manager_converter_access_role_prod.id
  ]
}