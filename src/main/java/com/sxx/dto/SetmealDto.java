package com.sxx.dto;

import com.sxx.entity.Setmeal;
import com.sxx.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
