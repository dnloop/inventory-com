package io.github.dnloop.inventorycom.utils;

public enum RouteMain implements PathMain {
	MAIN {
		@Override
		public String dataEntry() {
			return "/fxml/main/dataEntry.fxml";
		}

		@Override
		public String mainGUI() {
			return "/fxml/main/mainGui.fxml";
		}

		@Override
		public String about() {
			return "/fxml/main/about.fxml";
		}
	}
}
