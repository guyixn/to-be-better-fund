<template>
	<div>
		<n-grid :x-gap="16" :y-gap="16" :item-responsive="true">
			<n-grid-item span="0:24 640:24 1024:8">
				<n-card title="时间线" :bordered="false" class="rounded-16px shadow-sm">
					<div class="h-360px">
						<n-timeline>
							<n-timeline-item v-for="item in timelines" :key="item.type" v-bind="item"/>
						</n-timeline>
					</div>
				</n-card>
			</n-grid-item>
			<n-grid-item span="0:24 640:24 1024:16">
				<n-card title="表格" :bordered="false" class="rounded-16px shadow-sm">
					<div class="h-360px">
						<n-data-table size="small" :columns="columns" :data="tableData"/>
					</div>
				</n-card>
			</n-grid-item>
		</n-grid>
		<vxe-table border :data="tableDataVxe">
			<vxe-column type="seq" width="60"></vxe-column>
			<vxe-column field="name" title="Name">
				<template #default="{ row }">
					<span>自定义插槽模板 {{ row.name }}</span>
				</template>
			</vxe-column>
			<vxe-column field="sex" title="Sex"></vxe-column>
			<vxe-column field="age" title="Age"></vxe-column>
		</vxe-table>
	</div>
</template>

<script setup lang="ts">
import {h} from 'vue';
import {NTag} from 'naive-ui';

interface TimelineData {
	type: 'default' | 'info' | 'success' | 'warning' | 'error';
	title: string;
	content: string;
	time: string;
}

const tableDataVxe = [
	{id: 10001, name: 'Test1', role: 'Develop', sex: 'Man', age: 28, address: 'test abc'},
	{id: 10002, name: 'Test2', role: 'Test', sex: 'Women', age: 22, address: 'Guangzhou'},
	{id: 10003, name: 'Test3', role: 'PM', sex: 'Man', age: 32, address: 'Shanghai'},
	{id: 10004, name: 'Test4', role: 'Designer', sex: 'Women', age: 24, address: 'Shanghai'}
];
const gridOptions = {
	border: true,
	columns: [
		{type: 'seq', width: 50},
		{field: 'name', title: 'Name', slots: {default: 'name'}},
		{field: 'sex', title: 'Sex', showHeaderOverflow: true},
		{field: 'address', title: 'Address', showOverflow: true}
	],
	data: [
		{id: 10001, name: 'Test1', role: 'Develop', sex: 'Man', age: 28, address: 'test abc'},
		{id: 10002, name: 'Test2', role: 'Test', sex: 'Women', age: 22, address: 'Guangzhou'},
		{id: 10003, name: 'Test3', role: 'PM', sex: 'Man', age: 32, address: 'Shanghai'},
		{id: 10004, name: 'Test4', role: 'Designer', sex: 'Women', age: 24, address: 'Shanghai'}
	]
};

interface TableData {
	key: number;
	name: string;
	age: number;
	address: string;
	tags: string[];
}

const timelines: TimelineData[] = [
	{type: 'default', title: '啊', content: '', time: '2021-10-10 20:46'},
	{type: 'success', title: '成功', content: '哪里成功', time: '2021-10-10 20:46'},
	{type: 'error', title: '错误', content: '哪里错误', time: '2021-10-10 20:46'},
	{type: 'warning', title: '警告', content: '哪里警告', time: '2021-10-10 20:46'},
	{type: 'info', title: '信息', content: '是的', time: '2021-10-10 20:46'}
];

const columns = [
	{
		title: 'Name',
		key: 'name'
	},
	{
		title: 'Age',
		key: 'age'
	},
	{
		title: 'Address',
		key: 'address'
	},
	{
		title: 'Tags',
		key: 'tags',
		render(row: TableData) {
			const tags = row.tags.map(tagKey => {
				return h(
					NTag,
					{
						style: {
							marginRight: '6px'
						},
						type: 'info'
					},
					{
						default: () => tagKey
					}
				);
			});
			return tags;
		}
	}
];

const tableData: TableData[] = [
	{
		key: 0,
		name: 'John Brown',
		age: 32,
		address: 'New York No. 1 Lake Park',
		tags: ['nice', 'developer']
	},
	{
		key: 1,
		name: 'Jim Green',
		age: 42,
		address: 'London No. 1 Lake Park',
		tags: ['wow']
	},
	{
		key: 2,
		name: 'Joe Black',
		age: 32,
		address: 'Sidney No. 1 Lake Park',
		tags: ['cool', 'teacher']
	},
	{
		key: 3,
		name: 'Soybean',
		age: 25,
		address: 'China Shenzhen',
		tags: ['handsome', 'programmer']
	},
	{
		key: 4,
		name: 'John Brown',
		age: 32,
		address: 'New York No. 1 Lake Park',
		tags: ['nice', 'developer']
	},
	{
		key: 5,
		name: 'Jim Green',
		age: 42,
		address: 'London No. 1 Lake Park',
		tags: ['wow']
	},
	{
		key: 6,
		name: 'Joe Black',
		age: 32,
		address: 'Sidney No. 1 Lake Park',
		tags: ['cool', 'teacher']
	}
];
</script>

<style scoped></style>
