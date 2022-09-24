<template>
	<div>
		<n-form
			ref="formRef"
			inline
			:label-width="80"
			:model="formValue"
			label-placement="left"
			:style="{
        maxWidth: '640px'
      }"
			:max-height="250"
			:scroll-x="1800"
		>
			<n-grid :cols="24" :x-gap="24">
				<n-form-item-gi :span="12" label="基金代码" path="fundCode">
					<n-input v-model:value="formValue.fundCode" placeholder="请输入"/>
				</n-form-item-gi>
				<n-form-item-gi :span="12">
					<n-button @click="handleValidateClick">查询</n-button>
				</n-form-item-gi>
			</n-grid>
		</n-form>
		<n-data-table
			remote
			striped
			size="small"
			ref="table"
			:single-line="false"
			:bordered="false"
			:columns="columns"
			:data="fundList"
			:loading="loading"
			:pagination="pagination"
			@update:sorter="handleSorterChange"
			@update:filters="handleFiltersChange"
			@update:page="handlePageChange"
		/>
	</div>
</template>

<script setup lang="ts">
import {reactive, ref, onMounted} from "vue";
import {FormInst, FormItemRule, useMessage,DataTableColumn} from 'naive-ui'
import {request} from '@/service/request';

const formValue = reactive({
	fundCode: ''
});



const treeSelectOptions = [
	{
		label: 'Rubber Soul',
		key: 'Rubber Soul',
	},
	{
		label: 'Rubber 2',
		key: 'Rubber 2',
	}
];

const formRef = ref<FormInst | null>()
const message = useMessage()

function handleValidateClick(e: MouseEvent) {
	e.preventDefault()
	getData(1, 15, 'ascend', {"fundCode": formValue.fundCode});
}

// 用户信息 --- reactive 用来绑定复杂数据类型
interface IFundList {
	id: number,
	fund_code: string,
	fund_name: string,
	fund_type: string,
	create_date: string,
	create_time: string
}

const loading = false;

interface IPagination {
	page: number,
	pageCount: number,
	pageSize: number,
	itemCount: number,
	prefix: () => string
}

const pagination: IPagination = reactive<IPagination>({
	page: 1,
	pageCount: 1,
	pageSize: 15,
	itemCount: 0,
	prefix() {
		return `Total is ${pagination.itemCount}`
	}
});

const fundList: IFundList[] = reactive<IFundList[]>([]);

const columns = [
	{
		title: 'Id',
		key: 'id',
		sorter: true,
		sortOrder: true,
		width:180
	},
	{
		title: '基金代码',
		key: 'fund_code',
		width:180,
		sorter: true,
		filter: true,
		sortOrder: true
	},
	{
		title: '基金名称',
		key: 'fund_name',
		width:180
	},
	{
		title: '基金类型',
		key: 'fund_type',
		width:180
	},
	{
		title: '创建日期',
		key: 'create_date',
		width:180
	}
];

async function getData(page: number, pageSize = 15, order = 'ascend', filter = {}) {
	const response = await request.post("http://localhost:5002/api/fund/list/query", {
		"page": page,
		"pageSize": pageSize,
		"order": order,
		...filter
	});
	fundList.length = 0;
	let i = 0;
	for (const v of response.data.list) {
		fundList.push(v);
	}
	pagination.itemCount = response.data.total;
	pagination.page = page;
	pagination.pageSize = pageSize;
	pagination.pageCount = response.data.total / pageSize + response.data.total % pageSize;
	console.log(pagination)
}

onMounted(() => {
	getData(1);
});


function handleSorterChange(sorter: { columnKey: string; }) {
	console.log(sorter);
	console.log(pagination)
}

function handleFiltersChange(filters: any) {
	console.log(filters)
}

function handlePageChange(currentPage: any) {
	getData(currentPage);
	console.log(currentPage);
}
</script>
