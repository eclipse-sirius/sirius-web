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

export const More = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M11.667 0A2.675 2.675 0 009 2.667c0 1.466 1.2 2.666 2.667 2.666 1.466 0 2.666-1.2 2.666-2.666C14.333 1.2 13.133 0 11.667 0zm0 18.667A2.674 2.674 0 009 21.333C9 22.8 10.2 24 11.667 24c1.466 0 2.666-1.2 2.666-2.667 0-1.466-1.2-2.666-2.666-2.666zm0-9.334A2.674 2.674 0 009 12c0 1.467 1.2 2.667 2.667 2.667 1.466 0 2.666-1.2 2.666-2.667 0-1.467-1.2-2.667-2.666-2.667z"></path>
    </svg>
  );
};
More.propTypes = propTypes;
