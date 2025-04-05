package com.marketplace.crossproduct.core.usecase;

public interface UseCase<I extends UseCaseInput, O extends UseCaseOutput> {

    O execute(I input);

}
