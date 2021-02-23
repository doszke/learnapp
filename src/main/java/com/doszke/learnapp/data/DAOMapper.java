package com.doszke.learnapp.data;

import com.doszke.learnapp.data.dao.Mappable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DAOMapper {

    public List<String[]> mapList(List<? extends Mappable> coll) {
        return coll.stream().map(Mappable::map).collect(Collectors.toList());
    }

}