/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import AccountTreeIcon from '@mui/icons-material/AccountTree';
import ArrowDropDownCircleIcon from '@mui/icons-material/ArrowDropDownCircle';
import BarChartIcon from '@mui/icons-material/BarChart';
import CheckBoxIcon from '@mui/icons-material/CheckBox';
import EventIcon from '@mui/icons-material/Event';
import FormatListBulletedIcon from '@mui/icons-material/FormatListBulleted';
import ImageIcon from '@mui/icons-material/Image';
import LabelOutlinedIcon from '@mui/icons-material/LabelOutlined';
import LinearScaleOutlinedIcon from '@mui/icons-material/LinearScaleOutlined';
import LinkIcon from '@mui/icons-material/Link';
import PieChartIcon from '@mui/icons-material/PieChart';
import RadioButtonCheckedIcon from '@mui/icons-material/RadioButtonChecked';
import TextFieldsIcon from '@mui/icons-material/TextFields';
import TextFormatIcon from '@mui/icons-material/TextFormat';
import ViewColumnIcon from '@mui/icons-material/ViewColumn';
import { WidgetDescriptor } from './FormDescriptionEditorRepresentation.types';
import { Button } from './icons/Button';

export const coreWidgets: WidgetDescriptor[] = [
  {
    name: 'BarChart',
    label: 'Bar Chart',
    icon: <BarChartIcon />,
  },
  {
    name: 'Button',
    icon: <Button width={'24px'} height={'24px'} />,
  },
  {
    name: 'Checkbox',
    icon: <CheckBoxIcon />,
  },
  {
    name: 'FlexboxContainer',
    label: 'Flexbox Container',
    icon: <ViewColumnIcon width={'24px'} height={'24px'} />,
  },
  {
    name: 'Image',
    icon: <ImageIcon width={'24px'} height={'24px'} />,
  },
  {
    name: 'Label',
    icon: <LabelOutlinedIcon />,
  },
  {
    name: 'Link',
    icon: <LinkIcon />,
  },
  {
    name: 'List',
    icon: <FormatListBulletedIcon />,
  },
  {
    name: 'MultiSelect',
    icon: <ArrowDropDownCircleIcon />,
  },
  {
    name: 'PieChart',
    label: 'Pie Chart',
    icon: <PieChartIcon />,
  },
  {
    name: 'Radio',
    icon: <RadioButtonCheckedIcon />,
  },
  {
    name: 'RichText',
    icon: <TextFormatIcon />,
  },
  {
    name: 'Select',
    icon: <ArrowDropDownCircleIcon />,
  },
  {
    name: 'Slider',
    icon: <LinearScaleOutlinedIcon />,
  },
  {
    name: 'Tree',
    icon: <AccountTreeIcon />,
  },
  {
    name: 'SplitButton',
    label: 'Split Button',
    icon: <Button width={'24px'} height={'24px'} />,
  },
  {
    name: 'TextArea',
    label: 'Text Area',
    icon: <TextFieldsIcon />,
  },
  {
    name: 'Textfield',
    icon: <TextFieldsIcon />,
  },
  {
    name: 'DateTime',
    icon: <EventIcon />,
  },
];
