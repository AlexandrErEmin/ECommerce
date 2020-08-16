package com.test.ecommerce.interfaces;

public interface IForRecycler {
    interface IRecycler{
        /**
         * Клики по карточкам в Recycler
         * @param idCard НЕ порядковый номер карточки, А ID ТОВАРА!!!!
         * @param positionCard  ПОРЯДКОВЫЙ НОМЕР КАРТОЧКИ
         */
        void clickRecycler(int idCard, int positionCard);
    }
}
