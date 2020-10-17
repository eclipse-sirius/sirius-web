/*******************************************************************************
 * Copyright (c) 2020 Remix Design Studio, Obeo and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *
 * Contributors:
 *     Remix Design Studio - initial implementation as SVG
 *     Obeo - conversion into JSX
 *******************************************************************************/
import React from 'react';
import PropTypes from 'prop-types';

const propTypes = {
  title: PropTypes.string.isRequired,
};
export const Warning = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 22"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M12.9873 0.569968L23.8473 19.3806C23.9473 19.5539 24 19.7505 24 19.9506C24 20.1507 23.9473 20.3473 23.8473 20.5206C23.7472 20.6939 23.6033 20.8378 23.43 20.9379C23.2567 21.0379 23.0601 21.0906 22.86 21.0906H1.14001C0.939892 21.0906 0.743303 21.0379 0.57 20.9379C0.396696 20.8378 0.252784 20.6939 0.152729 20.5206C0.052673 20.3473 -1.27095e-06 20.1507 0 19.9506C1.27099e-06 19.7505 0.0526782 19.5539 0.152736 19.3806L11.0127 0.569968C11.1128 0.396674 11.2567 0.252771 11.43 0.152721C11.6033 0.0526714 11.7999 0 12 0C12.2001 0 12.3967 0.0526714 12.57 0.152721C12.7433 0.252771 12.8872 0.396674 12.9873 0.569968ZM10.86 15.3904V17.6705H13.14V15.3904H10.86ZM10.86 7.41019V13.1104H13.14V7.41019H10.86Z"></path>
    </svg>
  );
};
Warning.propTypes = propTypes;
