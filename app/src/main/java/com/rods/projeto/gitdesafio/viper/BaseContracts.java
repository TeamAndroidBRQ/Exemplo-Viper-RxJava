package com.rods.projeto.gitdesafio.viper;

public class BaseContracts {

    public interface Presenter {
        void detach();
        void detachUI();
        void attachUI();
    }

}
