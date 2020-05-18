cmake_minimum_required(VERSION 3.4.1)

project(device)

add_library( # Sets the name of the library.
        device
        # Sets the library as a shared library.
        SHARED
        # Provides a relative path to your source file(s).
        device.cpp
        device.h
        )

find_library(log-lib log)


target_link_libraries(
        device
        ${log-lib})