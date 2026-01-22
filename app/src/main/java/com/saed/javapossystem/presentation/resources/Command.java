package com.saed.javapossystem.presentation.resources;

public interface Command<T>{
    public void execute(T parameter);
}
