<template>
  <div class="dashboard">
    <div class="dashboard-wrap">
      <div id="main-title">
        <p>几米科技SMT防错料系统实时状况</p>
        <p>Powered by 几米物联自动化部</p>
      </div>
      <div class="dashboard-canvas-container">
        <div class="dashboard-canvas-content">
          <canvas id="dashboard-canvas" width="136" height="54"></canvas>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex';
  import {getChartsConf} from "../config/ChartsConfig";
  import {statusDetails} from "../store/getters";

  export default {
    name: "Dashboard",
    data() {
      return {
        mark: 0,
          lData:[[],[],[],[],[],[],[],[]],
        updatedConfig: {
          name: '',
          data: this.lData,
          label: []
        },
          myChart:''
      };
    },
      watch:{
        statusDetails:function () {
            this.initData();
            this.getLineData();
            this.updateChart(this.myChart);
        }
      },
    computed:{
      ...mapGetters(['lineSize','lineStatusData','statusDetails']),
    },
    mounted() {
      this.$nextTick(() => {
       let ctx = document.getElementById("dashboard-canvas");
       let config = JSON.parse(JSON.stringify(getChartsConf('bar')));
       let dashboardChart;
       this.initData();
       this.getLineData();
       this.initChart(ctx, config, this.updatedConfig).then((val) => {
        dashboardChart = val;
        this.myChart = val;
      });
       this.myInterval = setInterval(() => {
        this.initData();
        this.getLineData();
        this.updateChart(dashboardChart);
      }, 60000);
     })


    },
    methods: {
      initData: function () {
        this.updatedConfig = {
          name: '',
          data: this.lData,
          label: []
        }
      },
      initChart: function (ctx, srcConfig, updatedConfig) {
        return new Promise(resolve => {
          let tempConfig = JSON.parse(JSON.stringify(srcConfig));
          tempConfig.options.title.text = updatedConfig.name;
          tempConfig.data.labels = updatedConfig.label;
          for (let i = 0; i < updatedConfig.data.length; i++) {
            tempConfig.data.datasets[i].data = updatedConfig.data[i]
          }
          resolve(new this.Chart(ctx, tempConfig))
        })
      },
      updateChart: function (chart) {
        chart.options.title.text = this.updatedConfig.name;
        for (let i = 0; i < this.statusDetails.length; i++) {
          chart.data.datasets[i].data = this.updatedConfig.data[i]
        }
        chart.data.labels = this.updatedConfig.label;
        chart.update()
      },
      getLineData: function () {
        this.updatedConfig.name = '产线运行情况';
        for (let i = 0; i < this.statusDetails.length; i++) {
          /*label init*/
          this.updatedConfig.label.unshift(this.statusDetails[i].line + ' - ' + this.statusDetails[i].workOrder);
          /*data init*/
          this.updatedConfig.data[0].unshift(this.statusDetails[i].feedSuc);
          this.updatedConfig.data[1].unshift(this.statusDetails[i].feedFail);
          this.updatedConfig.data[2].unshift(this.statusDetails[i].changeSuc);
          this.updatedConfig.data[3].unshift(this.statusDetails[i].changeFail);
          this.updatedConfig.data[4].unshift(this.statusDetails[i].someSuc);
          this.updatedConfig.data[5].unshift(this.statusDetails[i].someFail);
          this.updatedConfig.data[6].unshift(this.statusDetails[i].allsSuc);
          this.updatedConfig.data[7].unshift(this.statusDetails[i].allsFail);

        }
      }
    }
  };
</script>

<style scoped>
.dashboard {
  position: relative;
  width: 14.4rem;
  height: 7.2rem;
  background-color: #333;
}

.dashboard-wrap {
  position: absolute;
  background-color: #333333;
  width: 14rem;
  height: 6.8rem;
  margin: 0.2rem 0.15rem;
  box-shadow: #222 0.05rem 0.05rem 0.5rem;
}

#main-title {
  position: absolute;
  width: 14rem;
  height: 1rem;
  color: #fff;
  font-size: 0.16rem;
  background: #0FD2FE;
  box-shadow: #222 0.02rem 0 0.5rem;
}

#main-title * {
  margin: 0;
}

#main-title p{
  text-align: center;
}

#main-title p:first-child{
 margin: 0.2rem 0 0.1rem;
 font-size:0.28rem;

}

.dashboard-canvas-container {
  position: relative;
  display: flex;
  margin-top: 1.2rem;
  height: 5.4rem;
  width: 14rem;
  justify-content: space-around;
}

.dashboard-canvas-content {
  width: 13.6rem;
  height: 5.4rem;
}
</style>
