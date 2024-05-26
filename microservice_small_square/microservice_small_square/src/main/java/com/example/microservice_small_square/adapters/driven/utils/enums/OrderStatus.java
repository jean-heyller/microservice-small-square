package com.example.microservice_small_square.adapters.driven.utils.enums;


public enum OrderStatus {
    PENDING {
        @Override
        public OrderStatus next() {
            return PREPARATION;
        }

        @Override
        public OrderStatus previous() {
            return PENDING;
        }
    },
    PREPARATION {
        @Override
        public OrderStatus next() {
            return READY;
        }

        @Override
        public OrderStatus previous() {
            return PENDING;
        }
    },
    READY {
        @Override
        public OrderStatus next() {
            return DELIVERED;
        }

        @Override
        public OrderStatus previous() {
            return PREPARATION;
        }
    },
    DELIVERED {
        @Override
        public OrderStatus next() {
            return null;
        }

        @Override
        public OrderStatus previous() {
            return READY;
        }
    };

    public abstract OrderStatus next();
    public abstract OrderStatus previous();
}