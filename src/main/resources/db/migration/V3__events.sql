CREATE TABLE IF NOT EXISTS event_publication
(
    id               SERIAL                   NOT NULL,
    session_id       TEXT                     NOT NULL,
    event_type       TEXT                     NOT NULL,
    serialized_event TEXT                     NOT NULL,
    publication_date TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS event_publication_by_completion_date_idx ON event_publication (publication_date);