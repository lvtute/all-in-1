import { useLocation } from "react-router-dom";

const useQuery = () => {
  return new URLSearchParams(useLocation().search);
};

const queryParamUtils = {
  useQuery,
};

export default queryParamUtils;
