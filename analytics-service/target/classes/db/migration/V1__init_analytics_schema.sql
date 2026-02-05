CREATE SCHEMA IF NOT EXISTS analytics;

CREATE TABLE analytics.reports (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    report_type     VARCHAR(50) NOT NULL,
    period_from     DATE,
    period_to       DATE,
    payload         JSONB,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_reports_type ON analytics.reports(report_type);
CREATE INDEX idx_reports_created ON analytics.reports(created_at);
