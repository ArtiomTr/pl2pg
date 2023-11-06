ECHO OFF

rem Check if docker is installed
where /q docker
IF ERRORLEVEL 1 (
    rem Docker is not installed - check if WSL is available
    where /q wsl

    IF ERRORLEVEL 1 (
        rem WSL is not available - check for maven
        where /q mvn

        IF ERRORLEVEL 1 (
            rem Docker, WSL and maven not found - unable to build application
            ECHO Docker and maven missing - install one of them to proceed.
            EXIT /B
        ) ELSE (
            ECHO Found maven. Compiling project

            mvn clean install
        )
    ) ELSE (
        rem Check if WSL has docker installation
        wsl which docker
        IF ERRORLEVEL 1 (
            rem WSL doesn't have docker installation, try to find maven
            where /q mvn

            IF ERRORLEVEL 1 (
                rem Docker, WSL and maven not found - unable to build application
                ECHO Docker and maven missing - install one of them to proceed.
                EXIT /B
            ) ELSE (
                ECHO Found maven. Compiling project

                mvn clean install
            )
        ) ELSE (
            ECHO Found docker in WSL. Proceeding with docker.

            wsl docker build . -f Dockerfile -t pl2pg --progress plain
        )
    )
) ELSE (
    ECHO Found docker installation. Proceeding with docker.

    docker build . -f Dockerfile -t pl2pg --progress plain
)
