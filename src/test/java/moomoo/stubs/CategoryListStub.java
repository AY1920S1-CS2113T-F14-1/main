package moomoo.stubs;

import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;

import java.util.ArrayList;

public class CategoryListStub extends CategoryList {
    private ArrayList<Category> newArrayList;

    public CategoryListStub() {
        newArrayList =  new ArrayList<>();
    }

    @Override
    public void add(Category newCategory) {
        newArrayList.add(new CategoryStub("shoes"));
        newArrayList.add(new CategoryStub("food"));
        newArrayList.add(new CategoryStub("window"));
        newArrayList.add(new CategoryStub("places to go"));
        newArrayList.add(new CategoryStub("sweets"));
        newArrayList.add(new CategoryStub("laptop"));
    }

    @Override
    public ArrayList<Category> getCategoryList() {
        return newArrayList;
    }

    @Override
    public Category get(int i) {
        return newArrayList.get(i);
    }

    @Override
    public Category get(String value) {
        for (Category iterCategory : newArrayList) {
            if (iterCategory.name().equalsIgnoreCase(value)) {
                return iterCategory;
            }
        }
        return null;
    }

}
