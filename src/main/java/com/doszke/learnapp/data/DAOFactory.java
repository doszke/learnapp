package com.doszke.learnapp.data;

import com.doszke.learnapp.data.dao.CardCategoryDAO;
import com.doszke.learnapp.data.dao.CardDAO;
import lombok.Singular;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class DAOFactory {

    public CardDAO createCard(String[] data) {
        CardDAO res = new CardDAO();
        res.setFront(data[0]);
        res.setBack(data[1]);
        return res;
    }

    public CardCategoryDAO createCardCategory(String[] data) {
        CardCategoryDAO res = new CardCategoryDAO();
        res.setName(data[0]);
        return res;
    }

}
