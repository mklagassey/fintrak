-- ===================================================================
-- V1: Initial Schema for the Finance Service
-- ===================================================================
-- This script creates all necessary tables for the finance service.
-- It uses 'IF NOT EXISTS' to be idempotent, meaning it can be run
-- safely even if the tables already exist.
-- ===================================================================

-- Create the 'budgets' table to store user budget information.
CREATE TABLE IF NOT EXISTS budgets (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    limit_amount DECIMAL(19, 2) NOT NULL,
    current_amount DECIMAL(19, 2) NOT NULL DEFAULT 0,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

-- Create the 'transactions' table to store individual financial transactions.
CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    type VARCHAR(50) NOT NULL, -- Corresponds to the TransactionType ENUM ('INCOME', 'EXPENSE')
    category VARCHAR(255),
    date DATE NOT NULL
);

-- Add an index on user_id in the transactions table for faster lookups.
CREATE INDEX IF NOT EXISTS idx_transactions_user_id ON transactions(user_id);

