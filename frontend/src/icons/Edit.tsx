/*******************************************************************************
 * Copyright (c) 2020, 2022 Remix Design Studio, Obeo and others
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

export const Edit = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M13.222 5.113l5.665 5.667L5.665 24H0v-5.666L13.222 5.112v.001zm1.888-1.888L17.942.391a1.335 1.335 0 011.889 0l3.778 3.778a1.336 1.336 0 010 1.889L20.775 8.89 15.11 3.225z"></path>
    </svg>
  );
};
Edit.propTypes = propTypes;
