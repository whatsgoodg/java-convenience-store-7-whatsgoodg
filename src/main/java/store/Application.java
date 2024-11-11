package store;

import store.back.controller.ConvenienceStoreController;

public class Application {
    public static void main(String[] args) {
        ConvenienceStoreController convenienceStoreController = new ConvenienceStoreController();
        convenienceStoreController.run();
    }
}
