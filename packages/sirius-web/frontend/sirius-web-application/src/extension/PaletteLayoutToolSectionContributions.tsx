/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import {
  AlignBottomComponentTool,
  AlignLeftComponentTool,
  AlignMiddleComponentTool,
  AlignRightComponentTool,
  AlignTopComponentTool,
  ArrangeInColumnComponentTool,
  ArrangeInGridComponentTool,
  ArrangeInRowComponentTool,
  DistributeHorizontalComponentTool,
  DistributeVerticalComponentTool,
  JustifyHorizontalComponentTool,
  JustifyVerticallyComponentTool,
  MakeSameSizeComponentTool,
} from '@eclipse-sirius/sirius-components-diagrams';
import { PaletteToolContributionProps } from '@eclipse-sirius/sirius-components-palette';

export const layoutToolsContribution: PaletteToolContributionProps[] = [
  {
    canHandle: () => true,
    id: 'align-left',
    label: 'Align left',
    toolSectionId: 'siriusweb_palette_tool_section_layout',
    isSearchable: true,
    component: AlignLeftComponentTool,
  },
  {
    canHandle: () => true,
    id: 'align-right',
    label: 'Align right',
    toolSectionId: 'siriusweb_palette_tool_section_layout',
    isSearchable: true,
    component: AlignRightComponentTool,
  },
  {
    canHandle: () => true,
    id: 'align-top',
    label: 'Align top',
    toolSectionId: 'siriusweb_palette_tool_section_layout',
    isSearchable: true,
    component: AlignTopComponentTool,
  },
  {
    canHandle: () => true,
    id: 'align-middle',
    label: 'Align middle',
    toolSectionId: 'siriusweb_palette_tool_section_layout',
    isSearchable: true,
    component: AlignMiddleComponentTool,
  },
  {
    canHandle: () => true,
    id: 'align-bottom',
    label: 'Align bottom',
    toolSectionId: 'siriusweb_palette_tool_section_layout',
    isSearchable: true,
    component: AlignBottomComponentTool,
  },
  {
    canHandle: () => true,
    id: 'arrange-in-row',
    label: 'Arrange in row',
    toolSectionId: 'siriusweb_palette_tool_section_layout',
    isSearchable: true,
    component: ArrangeInRowComponentTool,
  },
  {
    canHandle: () => true,
    id: 'arrange-in-column',
    label: 'Arrange in column',
    toolSectionId: 'siriusweb_palette_tool_section_layout',
    isSearchable: true,
    component: ArrangeInColumnComponentTool,
  },
  {
    canHandle: () => true,
    id: 'arrange-in-grid',
    label: 'Arrange in grid',
    toolSectionId: 'siriusweb_palette_tool_section_layout',
    isSearchable: true,
    component: ArrangeInGridComponentTool,
  },
  {
    canHandle: () => true,
    id: 'distribute-horizontal',
    label: 'Distribute horizontally',
    toolSectionId: 'siriusweb_palette_tool_section_layout',
    isSearchable: true,
    component: DistributeHorizontalComponentTool,
  },
  {
    canHandle: () => true,
    id: 'distribute-vertical',
    label: 'Distribute vertically',
    toolSectionId: 'siriusweb_palette_tool_section_layout',
    isSearchable: true,
    component: DistributeVerticalComponentTool,
  },
  {
    canHandle: () => true,
    id: 'justify-horizontally',
    label: 'Justify horizontally',
    toolSectionId: 'siriusweb_palette_tool_section_layout',
    isSearchable: true,
    component: JustifyHorizontalComponentTool,
  },
  {
    canHandle: () => true,
    id: 'justify-vertically',
    label: 'Justify vertically',
    toolSectionId: 'siriusweb_palette_tool_section_layout',
    isSearchable: true,
    component: JustifyVerticallyComponentTool,
  },
  {
    canHandle: () => true,
    id: 'make-same-size',
    label: 'Make same, size',
    toolSectionId: 'siriusweb_palette_tool_section_layout',
    isSearchable: true,
    component: MakeSameSizeComponentTool,
  },
];
