package io.github.dnloop.inventorycom.utils;

public enum Route implements Path {

	LOADING {
		@Override
		public String display() {
			return "/fxml/util/loading.fxml";
		}

	}
}
