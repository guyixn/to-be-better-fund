import { App, createApp } = 'vue'
import XEUtils from 'xe-utils'

import {
	// 全局对象
	VXETable,

	// 表格功能
	Footer,
	// Icon,
	// Filter,
	// Edit,
	// Menu,
	// Export,
	// Keyboard,
	// Validator,

	// 可选组件
	Column,
	// Colgroup,
	// Grid,
	// Tooltip,
	// Toolbar,
	// Pager,
	// Form,
	// FormItem,
	// FormGather,
	// Checkbox,
	// CheckboxGroup,
	// Radio,
	// RadioGroup,
	// RadioButton,
	// Switch,
	// Input,
	// Select,
	// Optgroup,
	// Option,
	// Textarea,
	// Button,
	// Modal,
	// List,
	// Pulldown,

	// 表格
	Table
} from 'vxe-table'
import zhCN from 'vxe-table/es/locale/lang/zh-CN'

// 按需加载的方式默认是不带国际化的，自定义国际化需要自行解析占位符 '{0}'，例如：
VXETable.setup({
	i18n: (key, args) => XEUtils.toFormatString(XEUtils.get(zhCN, key), args)
})
