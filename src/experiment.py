#!/usr/bin/python2
# -*- coding: utf-8 -*-
import codecs
import re
import subprocess
import time
import os

_last_result = -1

def apply_last_result(result):
    global _last_result
    if (_last_result == -1):
        _last_result = result
        return ''
    s = (result / _last_result)
    _last_result = -1
    if (s > 1):
        return '(+'+str(round(s-1.0,3)*100)+'%)'
    elif (s < 1):
        return '(-'+str(round(1.0-s,3)*100)+'%)'
    return ''

def lock_type(lockType):
    if lockType == 's':
        return 'safe.ReentrantLock'
    return 'ReentrantLock'

def measure_execution_test(threads, counters, lock, nexecutions, repetitions, ignore_first_executions=0):
    elapsedTime = 0
    counts = {}
    cmd = ['java', '-cp', os.getenv('HOME') + '/out', 'safe.OverheadExperiment', str(threads), str(counters), lock, str(nexecutions)]
    print cmd
    accounted_repetitions = repetitions - ignore_first_executions
    for i in range(repetitions):        
        start = time.time()
        output = subprocess.check_output(cmd)
        if ignore_first_executions > 0:
            ignore_first_executions -= 1
            continue
        elapsedTime += (time.time() - start)
    avg_time = (elapsedTime / accounted_repetitions)

    f = codecs.open('results.txt', 'a', encoding='utf-8')
    f.write(u'Threads: %d, Contadores: %d, Limite de contagem por thread: %d, Lock: %s\n' % (threads, counters, nexecutions, lock_type(lock)))
    f.write(u'Repetições: %d (consideradas ultimas %d execucoes)\n' % (repetitions, accounted_repetitions))
    f.write(u'Tempo médio: %f s %s\n' % (avg_time, apply_last_result(avg_time)))

    f.write('\n')
    f.close()


if __name__ == '__main__':
    measure_execution_test(10, 10, 'r', 1000, 70, 20)
    measure_execution_test(10, 10, 's', 1000, 70, 20)

    measure_execution_test(50, 10, 'r', 1000, 70, 20)
    measure_execution_test(50, 10, 's', 1000, 70, 20)

    measure_execution_test(100, 10, 'r', 1000, 70, 20)
    measure_execution_test(100, 10, 's', 1000, 70, 20)

    measure_execution_test(200, 10, 'r', 1000, 70, 20)
    measure_execution_test(200, 10, 's', 1000, 70, 20)
