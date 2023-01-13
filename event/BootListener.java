package tn5250j.event;

import tn5250j.event.BootEvent;

import java.util.EventListener;

public interface BootListener extends EventListener {

    public abstract void bootOptionsReceived(BootEvent bootevent);

}
