package domain;

import java.util.UUID;

public class VendingMachine {
    private final UUID id;
    private MachineState state;

    public VendingMachine() {
        this.id = UUID.randomUUID();
        this.state = MachineState.IDLE;
    }

    public UUID getId() {
        return id;
    }

    public MachineState getState() {
        return state;
    }

    public void startAcceptingPayment() {
        transitionTo(MachineState.ACCEPTING_PAYMENT);
    }

    public void startDispensing() {
        transitionTo(MachineState.DISPENSING);
    }

    public void finishTransaction() {
        transitionTo(MachineState.IDLE);
    }

    public void takeOutOfService() {
        if (state != MachineState.IDLE) {
            throw new IllegalStateException(
                    "Machine must be idle before being taken out of service"
            );
        }
        state = MachineState.OUT_OF_SERVICE;
    }

    public void returnToService() {
        if (state != MachineState.OUT_OF_SERVICE) {
            throw new IllegalStateException(
                    "Machine is not out of service"
            );
        }
        state = MachineState.IDLE;
    }

    private void transitionTo(MachineState nextState) {
        boolean valid = switch (state) {
            case IDLE -> nextState == MachineState.ACCEPTING_PAYMENT;
            case ACCEPTING_PAYMENT -> nextState == MachineState.DISPENSING
                    || nextState == MachineState.IDLE;
            case DISPENSING -> nextState == MachineState.IDLE;
            case OUT_OF_SERVICE -> false;
        };

        if (!valid) {
            throw new IllegalStateException(
                    "Invalid state transition: " + state + " -> " + nextState
            );
        }

        state = nextState;
    }

    public void cancelTransaction() {
        if (state != MachineState.ACCEPTING_PAYMENT &&
                state != MachineState.DISPENSING) {
            throw new IllegalStateException(
                    "Cannot cancel transaction in state: " + state
            );
        }

        state = MachineState.IDLE;
    }
}