package com.ruber.service;

import com.ruber.dao.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import static com.ruber.dao.entity.OrderStatus.PAID;
import static com.ruber.dao.entity.OrderStatus.WAITING;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class FillDatabaseService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(
        propagation = REQUIRES_NEW,
        readOnly = false
    )
    public void fill() {
        try {
            File f1 = new File(null, new byte[]{4, 4, 4}, "fileWith4.png");
            File f2 = new File(null, new byte[]{5, 5, 5}, "fileWith5.png");
            File f3 = new File(null, new byte[]{6, 6, 6}, "fileWith6.png");

            entityManager.persist(f1);
            entityManager.persist(f2);
            entityManager.persist(f3);

            ExternalAppCredential cr1 = new ExternalAppCredential(null, 100, "100", "iPhone application");
            ExternalAppCredential cr2 = new ExternalAppCredential(null, 200, "200", "Android application");

            entityManager.persist(cr1);
            entityManager.persist(cr2);

            File f4 = new File(null, new byte[]{7, 7, 7}, "fileWith7.png");
            File f5 = new File(null, new byte[]{8, 8, 8}, "fileWith8.png");
            File f6 = new File(null, new byte[]{9, 9, 9}, "fileWith9.png");

            PinnedText t1 = new PinnedText(null, "text1");
            PinnedText t2 = new PinnedText(null, "text2");
            PinnedText t3 = new PinnedText(null, "text3");

            PinnedMessage m1 = new PinnedMessage(null, 777L);
            PinnedMessage m2 = new PinnedMessage(null, 888L);
            PinnedMessage m3 = new PinnedMessage(null, 999L);

            Customer c1 = new Customer(null, "Nika She", "+79214454546", 100200);
            Customer c2 = new Customer(null, "Iva Lee", "+79528951011", 300400);

            Shipment sh1 = new Shipment(null, "Moscow, Tverskaya, 45, 14", new BigDecimal("200.15"));

            VkToken v1 = new VkToken(null, "abc141516ff");
            VkToken v2 = new VkToken(null, "bbefcac7891");
            VkToken v3 = new VkToken(null, "ccbafee7055");

            RuberToken r1 = new RuberToken(null, "abc123");
            RuberToken r2 = new RuberToken(null, "def456");

            Discount d1 = new Discount(null, "A discount for you!", "Discount description", new URL("http://google.com/pic1.jpg"), new BigDecimal("-500.00"));

            ItemReplica ir1 = new ItemReplica(null, "itemReplica1", "itemReplica1 description", new URL("http://google.com/pic2.png"), new BigDecimal("100.54"), 2);
            ItemReplica ir2 = new ItemReplica(null, "itemReplica2", "itemReplica2 description", new URL("http://google.com/pic3.png"), new BigDecimal("400.12"), 11);

            VkItemReplica vir1 = new VkItemReplica(null, "vkItem1", "vkItem1 desc", new URL("http://google.com/pic4.gif"), new BigDecimal("10.00"), 1, 77889900, 55556666);
            VkItemReplica vir2 = new VkItemReplica(null, "vkItem2", "vkItem2 desc", new URL("http://google.com/pic5.gif"), new BigDecimal("700.00"), 3, 77889900, 77778888);
            VkItemReplica vir3 = new VkItemReplica(null, "vkItem3", "vkItem3 desc", new URL("http://google.com/pic6.gif"), new BigDecimal("25.05"), 4, 77889900, 22223333);

            Order o1 = new Order(null, "order1", "order1 desc", WAITING, 67890L, 10067890L, d1 ,sh1, Arrays.asList(ir1, vir1, vir2), c1, Arrays.asList(m1, m2, m3), Arrays.asList(t1, t2), Arrays.asList(f4, f5));
            Order o2 = new Order(null, "order2", null, PAID, 67890L, null, null, null, Arrays.asList(ir2, vir3), c1, new LinkedList<>(), Collections.singletonList(t3), Collections.singletonList(f6));

            User user = new User(null, 12345000, Arrays.asList(r1, r2), Arrays.asList(v1, v2, v3), Arrays.asList(o1, o2));

            entityManager.persist(user);

            System.out.println(user.getId());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
