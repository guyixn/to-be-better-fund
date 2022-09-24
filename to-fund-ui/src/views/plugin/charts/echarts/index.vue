<template>
  <n-space :vertical="true" :size="16">
    <n-card :bordered="false" class="rounded-16px shadow-sm">
      <div ref="simpleLineRef" class="h-400px"></div>
    </n-card>
    <n-button attr-type="button" @click="handleValidateClick">验证</n-button>
    <n-card :bordered="false" class="rounded-16px shadow-sm">
      <div ref="lineRef" class="h-400px"></div>
    </n-card>
  </n-space>
</template>

<script setup lang="ts">
import {ref, onUnmounted, onMounted, reactive} from 'vue';
import { graphic } from 'echarts';
import { useEcharts, type ECOption } from '@/composables';
import { request } from '@/service/request';

const simpleLineOptions = reactive<ECOption>({
  tooltip: {
    trigger: 'axis',
		backgroundColor: '#fff',
    axisPointer: {
      type: 'cross',
      label: {
        backgroundColor: '#6a7985'
      }
    }
  },
  title: {
    text: 'Stacked Line'
  },
  // legend: {
  // 	data: ['Email', 'Union Ads', 'Video Ads', 'Direct', 'Search Engine']
  // },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  toolbox: {
    feature: {
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      color: '#37a2da',
      name: 'Email',
      type: 'line',
      stack: 'Total',
      areaStyle: {
        color: {
          type: 'linear',
          x: 0,
          y: 0,
          x2: 0,
          y2: 1,
          colorStops: [
            {
              offset: 0.25,
              color: '#37a2da'
            },
            {
              offset: 1,
              color: '#fff'
            }
          ]
        }
      },
      emphasis: {
        focus: 'series'
      },
      data: []
    }
  ]
});

async function getData(page: number, pageSize = 10, order = 'ascend', filter = {}) {
  const response = await request.post('http://localhost:5002/api/fund/chart/query', {});
  const xAxis:any = {
    type: 'category',
    boundaryGap: false,
    data: []
  };
  const series:any = [
    {
      color: '#37a2da',
      name: '日增长率',
      type: 'bar',
      emphasis: {
        focus: 'series'
      },
      data: []
    },
		{
			color: '#1c0',
			name: '单位净值',
			type: 'line',
			emphasis: {
				focus: 'series'
			},
			data: []
		},
		{
			color: '#a67',
			name: '累计净值',
			type: 'line',
			emphasis: {
				focus: 'series'
			},
			data: []
		}
  ];
  for (const datum of response.data) {
    xAxis.data.push(datum.fund_date.replace(/-/g,'/'));
    series[0].data.push(datum.day_grow_rate);
    series[1].data.push(datum.unit_net);
    series[2].data.push(datum.sum_net);
  }
  simpleLineOptions.xAxis = xAxis;
  simpleLineOptions.series = series;
}

onMounted(() => {
  getData(1);
});

const { domRef: simpleLineRef } = useEcharts(simpleLineOptions);

function handleValidateClick(e: MouseEvent) {
  getData(1);
}

const lineOptions = ref<ECOption>({
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross',
      label: {
        backgroundColor: '#6a7985'
      }
    }
  },
  title: {
    text: 'Stacked Line'
  },
  // legend: {
  // 	data: ['Email', 'Union Ads', 'Video Ads', 'Direct', 'Search Engine']
  // },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  toolbox: {
    feature: {
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      color: '#37a2da',
      name: 'Email',
      type: 'line',
      smooth: true,
      stack: 'Total',
      areaStyle: {
        color: {
          type: 'linear',
          x: 0,
          y: 0,
          x2: 0,
          y2: 1,
          colorStops: [
            {
              offset: 0.25,
              color: '#37a2da'
            },
            {
              offset: 1,
              color: '#fff'
            }
          ]
        }
      },
      emphasis: {
        focus: 'series'
      },
      data: [120, 132, 101, 134, 90, 230, 210]
    },
    {
      color: '#9fe6b8',
      name: 'Union Ads',
      type: 'line',
      smooth: true,
      stack: 'Total',
      areaStyle: {
        color: {
          type: 'linear',
          x: 0,
          y: 0,
          x2: 0,
          y2: 1,
          colorStops: [
            {
              offset: 0.25,
              color: '#9fe6b8'
            },
            {
              offset: 1,
              color: '#fff'
            }
          ]
        }
      },
      emphasis: {
        focus: 'series'
      },
      data: [220, 182, 191, 234, 290, 330, 310]
    },
    {
      color: '#fedb5c',
      name: 'Video Ads',
      type: 'line',
      smooth: true,
      stack: 'Total',
      areaStyle: {
        color: {
          type: 'linear',
          x: 0,
          y: 0,
          x2: 0,
          y2: 1,
          colorStops: [
            {
              offset: 0.25,
              color: '#fedb5c'
            },
            {
              offset: 1,
              color: '#fff'
            }
          ]
        }
      },
      emphasis: {
        focus: 'series'
      },
      data: [150, 232, 201, 154, 190, 330, 410]
    },
    {
      color: '#fb7293',
      name: 'Direct',
      type: 'line',
      smooth: true,
      stack: 'Total',
      areaStyle: {
        color: {
          type: 'linear',
          x: 0,
          y: 0,
          x2: 0,
          y2: 1,
          colorStops: [
            {
              offset: 0.25,
              color: '#fb7293'
            },
            {
              offset: 1,
              color: '#fff'
            }
          ]
        }
      },
      emphasis: {
        focus: 'series'
      },
      data: [320, 332, 301, 334, 390, 330, 320]
    },
    {
      color: '#e7bcf3',
      name: 'Search Engine',
      type: 'line',
      smooth: true,
      stack: 'Total',
      areaStyle: {
        color: {
          type: 'linear',
          x: 0,
          y: 0,
          x2: 0,
          y2: 1,
          colorStops: [
            {
              offset: 0.25,
              color: '#e7bcf3'
            },
            {
              offset: 1,
              color: '#fff'
            }
          ]
        }
      },
      emphasis: {
        focus: 'series'
      },
      data: [820, 932, 901, 934, 1290, 1330, 1320]
    }
  ]
});
const { domRef: lineRef } = useEcharts(lineOptions);
</script>

<style scoped></style>
