<template>
  <div class="order-item">
    <img :src="headImage" alt="" class="head-image">
    <div class="order-title">{{title}}</div>
  </div>
</template>

<script>
export default {
  name: 'BicycleOrderItem',
  props: {
    id: Number,
    orderDetail: String
  },
  data () {
    const detail = JSON.parse(this.orderDetail)
    return {
      headImage: detail.headImage,
      title: detail.title
    }
  }
}
</script>

<style scoped lang="less">
  .head-image {
    width: 63px;
    height: 60px;
  }
  .order-item {
    display: flex;
    align-items: center;
  }
</style>
