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

export const Hide = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M3.70348 5.03669L0.235142 1.56946L1.8046 0L23.7649 21.9614L22.1954 23.5297L18.5241 19.8584C16.5733 21.0957 14.3101 21.751 12 21.7473C6.01941 21.7473 1.04372 17.4438 0 11.7649C0.476972 9.18131 1.77646 6.82122 3.70459 5.03669H3.70348ZM15.058 16.3923L13.4341 14.7685C12.8131 15.0656 12.1151 15.1628 11.4365 15.0466C10.7578 14.9304 10.132 14.6066 9.64511 14.1197C9.15826 13.6329 8.83442 13.007 8.71824 12.3284C8.60205 11.6498 8.69923 10.9518 8.9964 10.3307L7.37259 8.7069C6.66684 9.77335 6.35135 11.0509 6.47952 12.3233C6.6077 13.5957 7.17166 14.7847 8.07592 15.6889C8.98019 16.5932 10.1692 17.1572 11.4416 17.2853C12.7139 17.4135 13.9915 17.098 15.058 16.3923V16.3923ZM7.53452 2.62538C8.91765 2.08189 10.425 1.78242 12 1.78242C17.9806 1.78242 22.9563 6.08596 24 11.7649C23.6601 13.6124 22.8962 15.3559 21.7684 16.8581L17.487 12.5768C17.6142 11.7201 17.5389 10.8455 17.2673 10.0231C16.9957 9.20071 16.5352 8.45336 15.9228 7.84095C15.3104 7.22853 14.563 6.76806 13.7407 6.49643C12.9183 6.2248 12.0437 6.14956 11.187 6.27674L7.53452 2.62649V2.62538Z"></path>
    </svg>
  );
};
Hide.propTypes = propTypes;
