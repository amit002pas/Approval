package com.yodlee.sms.dataTypes;

public class AuthValidationResponse {

		private String authentication;
		private String token;
		private String displayName;
		private String reason;
		public String getAuthentication() {
			return authentication;
		}
		public void setAuthentication(String authentication) {
			this.authentication = authentication;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		@Override
		public String toString() {
			return "AuthValidationResponse [authentication=" + authentication + ", token=" + token + ", displayName="
					+ displayName + ", reason=" + reason + "]";
		}
}