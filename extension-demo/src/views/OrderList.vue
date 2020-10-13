<template>
  <div class="order-list-root">
    <div v-for="item in orderList" v-bind:key="item.id" class="order-item-wrap">
      <component v-bind:is="componentsMap[item.bizType]" v-bind="item"></component>
    </div>
  </div>
</template>

<script>

import AirportOrderItem from '@/components/orderList/AirportOrderItem.vue'
import BicycleOrderItem from '@/components/orderList/BicycleOrderItem.vue'
import PaymentOrderItem from '@/components/orderList/PaymentOrderItem.vue'
import TakeoutOrderItem from '@/components/orderList/TakeoutOrderItem.vue'

export default {
  name: 'OrderList',
  comments: {
    AirportOrderItem,
    BicycleOrderItem,
    PaymentOrderItem,
    TakeoutOrderItem
  },
  data () {
    return {
      componentsMap: {
        1: AirportOrderItem,
        2: BicycleOrderItem,
        3: PaymentOrderItem,
        4: TakeoutOrderItem
      },
      orderList: [{
        id: 1,
        orderDetail: '{ "title": "深圳 -> 北京", "headImage": "https://pic1.zhimg.com/v2-74256512edd894aa0e702aac3350308e.png" }',
        bizType: 1
      }, {
        id: 2,
        orderDetail: '{ "title": "单车", "headImage": "https://pic2.zhimg.com/v2-af9e540173c34b5f9428dff3bc3146f4.png" }',
        bizType: 2
      }, {
        id: 3,
        orderDetail: '{ "title": "潮梅里卤鹅 >", "headImage": "https://pic1.zhimg.com/v2-f4b3948baccde9757dac6e58681e5543.png" }',
        bizType: 3
      }, {
        id: 4,
        orderDetail: '{ "title": "幸福柚子炭火烤肉3-4人餐", "headImage": "https://pic4.zhimg.com/v2-4a33b84f571a5a956666212a5ff91f87.png" }',
        bizType: 4
      }]
    }
  }
}
</script>

<style scoped lang="less">
  .order-list-root {
    width: 400px;
    padding: 100px;
    margin: auto;
  }

  .order-item-wrap {
    height: 100px;
    margin: 5px;
    padding: 5px;
    background-color: #fff;
    border-radius: 5px;
  }
</style>
