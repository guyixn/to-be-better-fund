import { NO_ERROR_MSG_CODE, ERROR_MSG_DURATION } from '@/config';

/** 错误消息栈，防止同一错误同时出现 */
const errorMsgStack = new Map<string | number, string>([]);

function addErrorMsg(error: { msg: string; code: string; type: string }) {
  errorMsgStack.set(error.code, error.msg);
}
function removeErrorMsg(error: { msg: string; code: string; type: string }) {
  errorMsgStack.delete(error.code);
}
function hasErrorMsg(error: { msg: string; code: string; type: string }) {
  return errorMsgStack.has(error.code);
}

/**
 * 显示错误信息
 * @param error
 */
export function showErrorMsg(error: { msg: string; code: string; type: string }) {
  if (!error.msg) return;
  if (!NO_ERROR_MSG_CODE.includes(error.code)) {
    if (!hasErrorMsg(error)) {
      addErrorMsg(error);
      window.console.warn(error.code, error.msg);
      window.$message?.error(error.msg, { duration: ERROR_MSG_DURATION });
      setTimeout(() => {
        removeErrorMsg(error);
      }, ERROR_MSG_DURATION);
    }
  }
}
