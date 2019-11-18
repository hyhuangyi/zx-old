package cn.webapp.async.listener;

import cn.common.exception.ZXException;
import cn.webapp.async.event.HyTestEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Created by huangYi on 2018/8/17
 **/
@Component
public class HyTestListener {
    Logger logger=LoggerFactory.getLogger(this.getClass());
    /**
     * TransactionalEventListener  只有当前事务提交之后，才会执行事件监听器的方法（有事务情况下）
     * 异步方法里出现异常不会对触发方法事务有影响的
     * 如果不加异步情况下 （不使用@Async） 那么使用 @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)对事务没影响  使用@EventListener事务会回滚
     * @param hyTestEvent
     */
    @Async("myTaskAsyncPool")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void doTest(HyTestEvent hyTestEvent){
        logger.info(Thread.currentThread().getName()+hyTestEvent.getName() +"-"+hyTestEvent.getAge());
        if(1==1){
            throw new ZXException("假装异常");
        }
    }
}
