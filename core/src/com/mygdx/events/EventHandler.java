package com.mygdx.events;

import boost.GameObject;
import boost.GameObjectManager;
import com.badlogic.gdx.Gdx;
import com.mygdx.scenes.MySceneManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class EventHandler {

    public EventsHolder eventsHolder;

    public EventHandler(EventsHolder eventsHolder) {
        this.eventsHolder = eventsHolder;
    }

    public void handleEvents() {
        for (Event e: eventsHolder.popEventsToHandle()) {
            handleEvent(e);
        }
    }

    private void handleEvent(Event event) {
        String objectId = event.data.get(0);
        String method = event.data.get(1);
        ArrayList<Object> args = new ArrayList<>();
        ArrayList<Class> argsTypes = new ArrayList<>();
        for (int i = 2; i < event.data.size(); i+=2) {
            Object arg = null;
            switch (event.data.get(i)) {
                case "String": {
                    arg = event.data.get(i+1);
                    break;
                }
                case "int": {
                    arg = (int) Integer.parseInt(event.data.get(i+1));
                    break;
                }
                case "float": {
                    arg = (float) Float.parseFloat(event.data.get(i+1));
                    break;
                }
            }
            args.add(arg);
            argsTypes.add(arg.getClass());
        }
        Class[] argsTypesArray = new Class[argsTypes.size()];
        int i = 0;
        for (Class clazz: argsTypes) {
            argsTypesArray[i] = clazz;
            i++;
        }
        try {
            GameObject obj = GameObjectManager.gameObjects.get(objectId);
            Method myMethod = obj.getClass().getMethod(method, argsTypesArray);
            myMethod.invoke(obj, args.toArray());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
