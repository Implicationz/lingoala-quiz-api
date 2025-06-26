# LingoSphinx Quiz API

Quiz REST API for the LingoSphinx learning platform.

# Run local postgres container for development  
docker-compose up -d

# Dump development ddl for initialization of the database
docker exec -it lingosphinx-quiz-db pg_dump --host=localhost --port=5432 --username=dockeruser --schema-only --no-owner --no-privileges lingosphinx-quiz-db > schema.sql