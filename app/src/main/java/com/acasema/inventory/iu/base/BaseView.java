package com.acasema.inventory.iu.base;


/**
 * interfaz base para todas las clases
 * @param <T>
 */
public interface BaseView<T> {
    void setPresente(T presente);
    void showError(String error);
    void onSuccess();
}
