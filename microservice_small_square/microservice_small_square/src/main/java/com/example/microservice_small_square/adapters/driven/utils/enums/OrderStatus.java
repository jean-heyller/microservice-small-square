package com.example.microservice_small_square.adapters.driven.utils.enums;

public enum OrderStatus {
    PENDING {
        @Override
        public OrderStatus next() {
            return PREPARATION;
        }
    },
    PREPARATION {
        @Override
        public OrderStatus next() {
            return READY;
        }
    },
    READY {
        @Override
        public OrderStatus next() {
            return DELIVERED;
        }
    },
    DELIVERED {
        @Override
        public OrderStatus next() {
            return null;
        }
    };

    public abstract OrderStatus next();
}