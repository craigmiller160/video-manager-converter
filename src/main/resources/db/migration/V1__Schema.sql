CREATE TYPE convert_status AS ENUM ('pending', 'in_progress', 'completed', 'failed');

CREATE TABLE files_to_convert (
    id UUID NOT NULL,
    source_file TEXT NOT NULL,
    target_file TEXT NOT NULL,
    status convert_status NOT NULL,
    error_message TEXT,
    version INT NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
);