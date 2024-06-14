package com.access.util;

import com.access.model.FailureList;
import com.access.repo.FailuresCounterRepo;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;


public class SetupRedisMock {
    public static void setupRedisMock(FailuresCounterRepo failuresCounterRepo, HashMap<String, FailureList> redisMockMap){
        redisMockMap.clear();
        when(failuresCounterRepo.findById(any(String.class)))
                .then(new Answer<Optional<FailureList>>() {
                    @Override
                    public Optional<FailureList> answer(InvocationOnMock invocationOnMock) throws Throwable {
                        String ip = invocationOnMock.getArgument(0);
                        FailureList failureList = redisMockMap.get(ip);
                        return Optional.ofNullable(failureList);
                    }
                });
        when(failuresCounterRepo.save(any(FailureList.class)))
                .then(new Answer<FailureList>() {
                    @Override
                    public FailureList answer(InvocationOnMock invocationOnMock) throws Throwable {
                        FailureList failureList = invocationOnMock.getArgument(0);
                        redisMockMap.put(failureList.getSubnet(), failureList);
                        return failureList;
                    }
                });
        when(failuresCounterRepo.existsById(any(String.class)))
                .then(new Answer<Boolean>() {
                    @Override
                    public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                        String ip = invocationOnMock.getArgument(0);
                        return redisMockMap.containsKey(ip);
                    }
                });
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                String ip = invocation.getArgument(0);
                redisMockMap.remove(ip);
                return null;
            }
        }).when(failuresCounterRepo).deleteById(any(String.class));
    }

}
