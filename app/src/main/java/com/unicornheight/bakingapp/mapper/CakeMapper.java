package com.unicornheight.bakingapp.mapper;


import com.unicornheight.bakingapp.mvp.model.Cake;
import com.unicornheight.bakingapp.mvp.model.CakesResponse;
import com.unicornheight.bakingapp.mvp.model.CakesResponseIngredients;
import com.unicornheight.bakingapp.mvp.model.CakesResponseSteps;
import com.unicornheight.bakingapp.mvp.model.Storage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class CakeMapper {

    @Inject
    public CakeMapper() {
    }
    public List<Cake> mapCakes(Storage storage, List<CakesResponse> response) {
        List<Cake> cakeList = new ArrayList<>();
        if (response != null) {
            for (CakesResponse cake : response) {
                Cake myCake = new Cake();
                myCake.setId(cake.getId());
                myCake.setName(cake.getName());
                myCake.setServings(cake.getServings());
                myCake.setImage(cake.getImage());
                List<CakesResponseIngredients> responseCakes = cake.getIngredients();
                if (responseCakes != null) {
                    List<CakesResponseIngredients> cakeIngredientList = new ArrayList<>();
                    for (CakesResponseIngredients cakeIngredient : responseCakes) {
                        CakesResponseIngredients myCakeIngredient = new CakesResponseIngredients();
                        myCakeIngredient.setIngredient(cakeIngredient.getIngredient());
                        myCakeIngredient.setMeasure(cakeIngredient.getMeasure());
                        myCakeIngredient.setQuantity(cakeIngredient.getQuantity());
                        cakeIngredientList.add(myCakeIngredient);
                    }
                    myCake.setIngredients(cakeIngredientList);
                }
                List<CakesResponseSteps> responseStepCakes = cake.getSteps();
                if (responseStepCakes != null) {
                    List<CakesResponseSteps> cakeStepList = new ArrayList<>();
                    for (CakesResponseSteps cakeStepIngredient : responseStepCakes) {
                        CakesResponseSteps myCakeStep = new CakesResponseSteps();
                        myCakeStep.setDescription(cakeStepIngredient.getDescription());
                        myCakeStep.setVideoURL(cakeStepIngredient.getVideoURL());
                        myCakeStep.setThumbnailURL(cakeStepIngredient.getThumbnailURL());
                        myCakeStep.setShortDescription(cakeStepIngredient.getShortDescription());
                        cakeStepList.add(myCakeStep);
                    }
                    myCake.setSteps(cakeStepList);

                }
                storage.addCake(myCake);
                cakeList.add(myCake);
            }
        }
        return cakeList;
    }
}